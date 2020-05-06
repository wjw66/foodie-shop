package com.wjw.controller;

import com.wjw.CarouselService;
import com.wjw.CategoryService;
import com.wjw.enums.YesOrNo;
import com.wjw.pojo.Carousel;
import com.wjw.pojo.Category;
import com.wjw.pojo.vo.CategoryVO;
import com.wjw.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "获取首页轮播图列表")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
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
        List<Category> carousels = categoryService.queryAllRootLevelCat();
        return JSONResult.ok(carousels);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(@PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(list);
    }
}
