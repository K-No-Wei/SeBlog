package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.MenuDto;
import cn.knowei.sbg.entity.Menu;
import cn.knowei.sbg.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 19:13 2023/2/25
 */
@RestController
@RequestMapping("system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("list")
    public ResponseResult list(MenuDto menuDto){
        return menuService.list(menuDto);
    }

    @PostMapping()
    public ResponseResult add(@RequestBody Menu menu){
        return menuService.add(menu);
    }

    @GetMapping("{id}")
    public ResponseResult getMenu(@PathVariable("id") Long id){
        return ResponseResult.okResult(menuService.getById(id));
    }

    @PutMapping()
    public ResponseResult update(@RequestBody Menu menu){
        return menuService.update(menu);
    }

    @DeleteMapping("{menuId}")
    public ResponseResult delete(@PathVariable("menuId") Long menuId){
        return menuService.delete(menuId);
    }

    @GetMapping("treeselect")
    public ResponseResult treeselect(){
        return menuService.treeselect();
    }

    @GetMapping("roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable Long id){
        return menuService.roleMenuTreeselect(id);
    }
}
