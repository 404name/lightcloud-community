package com.juelunn.lightcloud.module.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juelunn.common.vo.*;
import com.juelunn.lightcloud.module.blog.dao.ArticleDao;
import com.juelunn.lightcloud.module.blog.dao.PhotoDao;
import com.juelunn.common.exception.BizException;
import com.juelunn.common.dto.PhotoBackDTO;
import com.juelunn.common.dto.PhotoDTO;
import com.juelunn.common.entity.Article;
import com.juelunn.common.entity.Photo;
import com.juelunn.common.entity.PhotoAlbum;
import com.juelunn.lightcloud.module.blog.service.PhotoAlbumService;
import com.juelunn.lightcloud.module.blog.service.PhotoService;
import com.juelunn.common.util.BeanCopyUtils;
import com.juelunn.common.util.PageUtils;
import com.juelunn.common.constant.CommonConst;
import com.juelunn.common.enums.PhotoAlbumStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 照片服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/04
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoDao, Photo> implements PhotoService {
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Override
    public PageResult<PhotoBackDTO> listPhotos(ConditionVO condition) {
        // 查询照片列表
        Page<Photo> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<Photo> photoPage = photoDao.selectPage(page, new LambdaQueryWrapper<Photo>()
                .eq(Objects.nonNull(condition.getAlbumId()), Photo::getAlbumId, condition.getAlbumId())
                .eq(Photo::getIsDelete, condition.getIsDelete())
                .orderByDesc(Photo::getId)
                .orderByDesc(Photo::getUpdateTime));
        List<PhotoBackDTO> photoList = BeanCopyUtils.copyList(photoPage.getRecords(), PhotoBackDTO.class);
        return new PageResult<>(photoList, (int) photoPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePhoto(PhotoInfoVO photoInfoVO) {
        Photo photo = BeanCopyUtils.copyObject(photoInfoVO, Photo.class);
        photoDao.updateById(photo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePhotos(PhotoVO photoVO) {
        List<Photo> photoList = photoVO.getPhotoUrlList().stream().map(item -> Photo.builder()
                        .albumId(photoVO.getAlbumId())
                        .photoName(IdWorker.getIdStr())
                        .photoSrc(item)
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(photoList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePhotosAlbum(PhotoVO photoVO) {
        List<Photo> photoList = photoVO.getPhotoIdList().stream().map(item -> Photo.builder()
                        .id(item)
                        .albumId(photoVO.getAlbumId())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePhotoDelete(DeleteVO deleteVO) {
        // 更新照片状态
        List<Photo> photoList = deleteVO.getIdList().stream().map(item -> Photo.builder()
                        .id(item)
                        .isDelete(deleteVO.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
        // 若恢复照片所在的相册已删除，恢复相册
        if (deleteVO.getIsDelete().equals(CommonConst.FALSE)) {
            List<PhotoAlbum> photoAlbumList = photoDao.selectList(new LambdaQueryWrapper<Photo>()
                            .select(Photo::getAlbumId)
                            .in(Photo::getId, deleteVO.getIdList())
                            .groupBy(Photo::getAlbumId))
                    .stream()
                    .map(item -> PhotoAlbum.builder()
                            .id(item.getAlbumId())
                            .isDelete(CommonConst.FALSE)
                            .build())
                    .collect(Collectors.toList());
            photoAlbumService.updateBatchById(photoAlbumList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePhotos(List<Integer> photoIdList) {
        photoDao.deleteBatchIds(photoIdList);
    }

    /**
     * 修改albumID直接查文章ID，不再去找相册了
     * @param albumId 相册id
     * @return
     */
    @Override
    public PhotoDTO listPhotosByAlbumId(Integer albumId) {
        // 查询相册信息
        Article article = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getId, albumId)
                .eq(Article::getIsDelete, CommonConst.FALSE)
                .eq(Article::getStatus, PhotoAlbumStatusEnum.PUBLIC.getStatus()));
        if (Objects.isNull(article)) {
            throw new BizException("卡片不存在");
        }
        // 查询照片列表
        Page<Photo> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        List<String> photoList = photoDao.selectPage(page, new LambdaQueryWrapper<Photo>()
                        .select(Photo::getPhotoSrc)
                        .eq(Photo::getAlbumId, albumId)
                        .eq(Photo::getIsDelete, CommonConst.FALSE)
                        .orderByDesc(Photo::getId))
                .getRecords()
                .stream()
                .map(Photo::getPhotoSrc)
                .collect(Collectors.toList());
        return PhotoDTO.builder()
                .photoAlbumCover(article.getArticleCover())
                .photoAlbumName(article.getArticleTitle())
                .photoList(photoList)
                .build();
    }

}




