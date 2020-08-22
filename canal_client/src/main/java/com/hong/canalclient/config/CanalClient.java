package com.hong.canalclient.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class CanalClient {
    //sql队列
    private Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();
    @Resource
    private DataSource dataSource;

    /**
     * canal入库方法
     */
    public void run() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            try {
                while (true) {
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        Thread.sleep(1000);
                    } else {
                        dataHandle(message.getEntries());
                    }
                    connector.ack(batchId);
                    //当队列里面堆积的sql大于一定数值的时候就模拟执行
                    if(SQL_QUEUE.size() >=1){
                        executeQueueSql();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        } finally {
            connector.disconnect();
        }
    }

    /**
     * 模拟执行队列里面的SQL语句
     */
    public void executeQueueSql(){
        int size = SQL_QUEUE.size();
        for(int i=0;i<size;i++){
            String sql = SQL_QUEUE.poll();
            System.out.println("[sql]------->"+sql);
            this.execute(sql.toString());
        }
    }


    /**
     * 数据处理
     *
     * @param entrys
     * @throws InvalidProtocolBufferException
     */
    private void dataHandle(List<CanalEntry.Entry> entrys) throws InvalidProtocolBufferException {
        for (CanalEntry.Entry entry : entrys) {
            if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                if (eventType == CanalEntry.EventType.DELETE) {
                    saveDeleteSql(entry);
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    saveUpdateSql(entry);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    saveInsertSql(entry);
                }

            }
        }
    }

    /**
     * 保存更新数据
     *
     * @param entry
     */
    private void saveUpdateSql(CanalEntry.Entry entry) {
      try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("update" + entry.getHeader().getTableName() + " set ");
                for (int i = 0; i < afterColumnsList.size(); i++) {
                    sql.append(" " + afterColumnsList.get(i).getName()
                            + " = '" + afterColumnsList.get(i).getValue() + "'");
                    if (i != afterColumnsList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" where ");
                List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                for (CanalEntry.Column column : beforeColumnsList) {
                    if (column.getIsKey()) {
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                SQL_QUEUE.add(sql.toString());
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }


    /**
     * 保存删除数据
     *
     * @param entry
     */
    private void saveDeleteSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                StringBuffer sql = new StringBuffer("delete from " + entry.getHeader().getTableName() + " where ");
                for (CanalEntry.Column column : beforeColumnsList) {
                    if (column.getIsKey()) {
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                SQL_QUEUE.add(sql.toString());
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存保存数据
     *
     * @param entry
     */
    private void saveInsertSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("insert into " + entry.getHeader().getTableName() + " ( ");
                for (int i = 0; i < afterColumnsList.size(); i++) {
                    sql.append(afterColumnsList.get(i).getName());
                    if (i != afterColumnsList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" ) VALUES (");
                for (int i = 0; i < afterColumnsList.size(); i++) {
                    sql.append("'" + afterColumnsList.get(i).getValue() + "'");
                    if (i != afterColumnsList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" )");
                SQL_QUEUE.add(sql.toString());
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 入库
     *
     * @param sql
     */
    public void execute(String sql) {
        Connection con = null;
        try {
            if (sql == null) return;
            con = dataSource.getConnection();
            QueryRunner runner = new QueryRunner();
            int row = runner.execute(con, sql);
            System.out.println("execute: " + row);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }
}
