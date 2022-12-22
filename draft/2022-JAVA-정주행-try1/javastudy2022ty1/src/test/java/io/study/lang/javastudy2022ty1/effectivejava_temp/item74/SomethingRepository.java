package io.study.lang.javastudy2022ty1.effectivejava_temp.item74;

import java.util.List;

public interface SomethingRepository {

    /**
     * @param id  An Identifier for something
     * @return  List of String for something
     * @throws IllegalArgumentException - in case the given entity is null
     */
    public List<String> findSomethingAll(Long id);
}
