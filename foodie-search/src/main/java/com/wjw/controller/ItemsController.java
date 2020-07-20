package com.wjw.controller;

import com.wjw.service.ItemsEsService;
import com.wjw.util.EsPageResult;
import com.wjw.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wjwjava01@163.com
 * @date : 10:50 2020/7/19
 * @description :
 */
@Slf4j
@RestController
@RequestMapping("items")
public class ItemsController {
    private final Integer MAX_STR_LENGTH = 100;
    @Autowired
    private ItemsEsService itemsEsService;

    @GetMapping("/es/search")
    public JSONResult search(String keywords, String sort,
                             @RequestParam(required = false,defaultValue = "1")Integer page,
                             @RequestParam(required = false,defaultValue = "10") Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg(null);
        }

        if (StringUtils.isNotBlank(keywords) && keywords.length() > MAX_STR_LENGTH) {
            return JSONResult.errorMsg("输入的内容超过限制，请减少字数再试！");
        }
        //es分页从零开始，但前端默认传1
        page--;

        EsPageResult pageResult = itemsEsService.searchItems(keywords, sort, page, pageSize);

        return JSONResult.ok(pageResult);
    }

}
