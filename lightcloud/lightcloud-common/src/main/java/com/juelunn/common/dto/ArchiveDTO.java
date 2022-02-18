package com.juelunn.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 归档文章
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;
    /**
     * 内容
     */
    private String articleContent;
    /**
     * 文章类型
     */
    private Integer type;

    /**
     * 文章缩略图
     */
    private String articleCover;

}
