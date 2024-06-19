package org.example.guava;

import com.google.common.collect.HashBasedTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableTest {
    @Test
    void test() {
        // init
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("Redmi 30", "屏幕", "1.8英寸");
        table.put("Redmi 30", "电池", "1500mA");
        table.put("Redmi 30", "价格", "1000￥");
        table.put("iPhone 30", "屏幕", "1.5英寸");
        table.put("iPhone 30", "电池", "1000mA");
        table.put("iPhone 30", "价格", "1000￥");
        assertEquals("{Redmi 30={屏幕=1.8英寸, 电池=1500mA, 价格=1000￥}, iPhone 30={屏幕=1.5英寸, 电池=1000mA, 价格=1000￥}}",
                table.toString());

        // get
        assertEquals("1.8英寸", table.get("Redmi 30", "屏幕"));
        // row
        assertEquals("{屏幕=1.5英寸, 电池=1000mA, 价格=1000￥}", table.row("iPhone 30").toString());
        // column
        assertEquals("{Redmi 30=1000￥, iPhone 30=1000￥}", table.column("价格").toString());
        // cell
        assertEquals("[(Redmi 30,屏幕)=1.8英寸, (Redmi 30,电池)=1500mA, (Redmi 30,价格)=1000￥, (iPhone 30,屏幕)=1.5英寸, (iPhone 30,电池)=1000mA, (iPhone 30,价格)=1000￥]",
                table.cellSet().toString());
    }
}
