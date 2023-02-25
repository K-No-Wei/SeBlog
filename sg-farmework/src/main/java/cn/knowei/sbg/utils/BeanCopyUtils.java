package cn.knowei.sbg.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 16:31 2023/2/23
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){

    }

    public static <V> V copyBean(Object source, Class<V> clazz){
        System.out.println(source);
        V result = null;
        try{
            result = clazz.newInstance();
            //属性复制
            BeanUtils.copyProperties(source, result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static <O, V>List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
