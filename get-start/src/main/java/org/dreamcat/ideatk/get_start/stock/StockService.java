package org.dreamcat.ideatk.get_start.stock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Data;
import org.dreamcat.common.reflect.ObjectRandomGenerator;
import org.dreamcat.common.reflect.ObjectType;
import org.dreamcat.common.util.RandomUtil;

/**
 * @author Jerry Will
 * @version 2024-02-10
 */
public class StockService {

    private static final StockService INSTANCE = new StockService();

    public static StockService getInstance() {
        return INSTANCE;
    }

    public List<StockData> queryPresetStockData(List<String> gids) {
        ObjectRandomGenerator gen = new ObjectRandomGenerator();
        return IntStream.range(0, 30).boxed()
                .map(i -> (StockData) gen.generate(ObjectType.fromType(StockData.class)))
                .collect(Collectors.toList());
    }

    private static final List<String> urls = Arrays.asList(
            "http://image.sinajs.cn/newchart/min/n/sh601009.gif",
            "http://image.sinajs.cn/newchart/daily/n/sh601009.gif",
            "http://image.sinajs.cn/newchart/weekly/n/sh601009.gif",
            "http://image.sinajs.cn/newchart/monthly/n/sh601009.gif");

    public StockPicture queryPicture(String gid) {
        StockPicture picture =  new StockPicture();
        picture.setMinUrl(RandomUtil.chooseOne(urls));
        picture.setDayUrl(RandomUtil.chooseOne(urls));
        return picture;
    }

    @Data
    public static class StockPicture {

        private String minUrl;
        private String dayUrl;
    }

    @Data
    public static class StockData {

        private String name;
        private String gid;
        private String nowPri;
        private String increase;
        private String incrPer;
    }
}
