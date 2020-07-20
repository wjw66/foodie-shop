package com.wjw.service;

import com.wjw.util.EsPageResult;

public interface ItemsEsService {

    EsPageResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

}
