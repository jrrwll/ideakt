package org.dreamcat.ideatk.get_start.stock;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.dreamcat.ideatk.util.NotificationUtil;

/**
 * @author Jerry Will
 * @version 2024-02-10
 */
public class StockToolWindowForm {

    public JPanel panel;
    public JTable table;
    public JLabel picMin;
    public JLabel picDay;

    private final DefaultTableModel defaultTableModel = new DefaultTableModel(
            new Object[][]{}, new String[]{"股票", "代码", "最新", "涨跌", "涨幅"});

    public StockToolWindowForm() {
        // 初始数据
        table.setModel(defaultTableModel);
        addRows(StockDataState.getInstance().getGids());

        // 添加事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                Object value = table.getValueAt(row, 1);
                StockService.StockPicture picture = StockService.getInstance().queryPicture(value.toString());
                try {
                    // 分钟K线
                    picMin.setSize(545, 300);
                    picMin.setIcon(new ImageIcon(new URL(picture.getMinUrl())));

                    // 当日K线
                    picDay.setSize(545, 300);
                    picDay.setIcon(new ImageIcon(new URL(picture.getDayUrl())));
                } catch (MalformedURLException m) {
                    NotificationUtil.notify(m);
                }
            }
        });
    }

    public void addRows(List<String> gids) {
        // 查询
        List<StockService.StockData> dataList = StockService.getInstance().queryPresetStockData(gids);

        // 清空
        int rowCount = defaultTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            defaultTableModel.removeRow(0);
        }

        // 添加
        for (StockService.StockData data : dataList) {
            defaultTableModel.addRow(new String[]{data.getName(), data.getGid(), data.getNowPri(), data.getIncrease(), data.getIncrPer()});
            table.setModel(defaultTableModel);
        }
    }
}
