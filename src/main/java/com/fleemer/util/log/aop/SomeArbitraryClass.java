package com.fleemer.util.log.aop;

import static com.fleemer.util.log.aop.LogFormat.JSON;
import static com.fleemer.util.log.aop.LogFormat.TREE;
import static com.fleemer.util.log.aop.LogFormat.XML;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SomeArbitraryClass {
    public void closeConnection(@Log(TREE) Object str, @Log(TREE) String str1, @Log({TREE, XML, JSON}) List<Integer> val, boolean result) {

    }
}
