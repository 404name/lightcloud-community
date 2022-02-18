package com.juelunn.lightcloud.module.blog.service;


import com.juelunn.common.entity.Tag;
import com.juelunn.common.dto.TagBackDTO;

import com.juelunn.common.dto.TagDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.vo.TagVO;
import com.juelunn.common.dto.TagDTO;
import java.util.List;

/**
 * 标签服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/29
 */
public interface TagService extends IService<Tag> {

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    PageResult<TagDTO> listTags();

    /**
     * 查询后台标签
     *
     * @param condition 条件
     * @return {@link PageResult< TagBackDTO >} 标签列表
     */
    PageResult<TagBackDTO> listTagBackDTO(ConditionVO condition);

    /**
     * 搜索文章标签
     *
     * @param condition 条件
     * @return {@link List<TagDTO>} 标签列表
     */
    List<TagDTO> listTagsBySearch(ConditionVO condition);

    /**
     * 删除标签
     *
     * @param tagIdList 标签id集合
     */
    void deleteTag(List<Integer> tagIdList);

    /**
     * 保存或更新标签
     *
     * @param tagVO 标签
     */
    void saveOrUpdateTag(TagVO tagVO);

}
