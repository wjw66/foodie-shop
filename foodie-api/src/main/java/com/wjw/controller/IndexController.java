package com.wjw.controller;

import com.wjw.CarouselService;
import com.wjw.CategoryService;
import com.wjw.enums.YesOrNo;
import com.wjw.pojo.Carousel;
import com.wjw.pojo.Category;
import com.wjw.pojo.vo.CategoryVO;
import com.wjw.pojo.vo.NewItemsVO;
import com.wjw.utils.JSONResult;
import com.wjw.utils.JsonUtils;
import com.wjw.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 21:07 2020/5/5
 * @description :
 */
@Api(value = "首页", tags = "首页展示的相关接口")
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> carousels;
        String carousel = redisOperator.get("carousel");

        if (StringUtils.isNoneBlank(carousel)) {
            carousels = JsonUtils.jsonToList(carousel, Carousel.class);
            return JSONResult.ok(carousels);
        }
        carousels = carouselService.queryAll(YesOrNo.YES.type);
        redisOperator.set("carousel", JsonUtils.objectToJson(carousels));

        return JSONResult.ok(carousels);
    }

    /**
     * 首页分类展示需求
     * 1.第一次刷新主页查询大分类，渲染到首页
     * 2.如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类(一级分类)")
    @GetMapping("/cats")
    public JSONResult cats() {
        String cats = redisOperator.get("cats");
        if (StringUtils.isNoneBlank(cats)){
            return JSONResult.ok(JsonUtils.jsonToList(cats,Category.class));
        }
        List<Category> carousels = categoryService.queryAllRootLevelCat();
        redisOperator.set("cats",JsonUtils.objectToJson(carousels));
        return JSONResult.ok(carousels);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(@PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        String subCat = redisOperator.get("subCat:" + rootCatId);
        if (StringUtils.isNoneBlank(subCat)){
            return JSONResult.ok(JsonUtils.jsonToList(subCat,CategoryVO.class));
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        redisOperator.set("subCat" + rootCatId,JsonUtils.objectToJson(list));
        return JSONResult.ok(list);
    }
    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(list);
    }
}
