package com.juelunn.lightcloud.module.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juelunn.lightcloud.module.blog.dao.ArticleDao;
import com.juelunn.lightcloud.module.blog.dao.PhotoAlbumDao;
import com.juelunn.lightcloud.module.blog.dao.PhotoDao;
import com.juelunn.common.enums.PhotoAlbumStatusEnum;
import com.juelunn.common.exception.BizException;
import com.juelunn.common.dto.PhotoAlbumBackDTO;
import com.juelunn.common.dto.PhotoAlbumDTO;
import com.juelunn.common.entity.Article;
import com.juelunn.common.entity.Photo;
import com.juelunn.common.entity.PhotoAlbum;
import com.juelunn.lightcloud.module.blog.service.PhotoAlbumService;
import com.juelunn.common.util.BeanCopyUtils;
import com.juelunn.common.util.PageUtils;
import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.vo.PhotoAlbumVO;
import com.juelunn.common.constant.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * 相册服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/04
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumDao, PhotoAlbum> implements PhotoAlbumService {
    @Autowired
    private PhotoAlbumDao photoAlbumDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private PhotoDao photoDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        // 查询相册名是否存在
        PhotoAlbum album = photoAlbumDao.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                .select(PhotoAlbum::getId)
                .eq(PhotoAlbum::getAlbumName, photoAlbumVO.getAlbumName()));
        if (Objects.nonNull(album) && !album.getId().equals(photoAlbumVO.getId())) {
            throw new BizException("相册名已存在");
        }
        PhotoAlbum photoAlbum = BeanCopyUtils.copyObject(photoAlbumVO, PhotoAlbum.class);
        this.saveOrUpdate(photoAlbum);
    }

    @Override
    public PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVO condition) {
        // 查询相册数量
        Integer count = photoAlbumDao.selectCount(new LambdaQueryWrapper<PhotoAlbum>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), PhotoAlbum::getAlbumName, condition.getKeywords())
                .eq(PhotoAlbum::getIsDelete, CommonConst.FALSE));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询相册信息
        List<PhotoAlbumBackDTO> photoAlbumBackList = photoAlbumDao.listPhotoAlbumBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(photoAlbumBackList, count);
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumBackInfos() {
        List<PhotoAlbum> photoAlbumList = photoAlbumDao.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getIsDelete, CommonConst.FALSE));
        return BeanCopyUtils.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }

    @Override
    public PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId) {
        // 查询相册信息
        Article article = articleDao.selectById(albumId);
        // 查询照片数量
        Integer photoCount = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getIsDelete, CommonConst.FALSE));
        PhotoAlbumBackDTO build = PhotoAlbumBackDTO.builder()
                .albumCover(article.getArticleCover())
                .albumDesc(article.getArticleContent().substring(0, 20) + "...")
                .albumName(article.getArticleTitle())
                .id(article.getId())
                .photoCount(photoCount)
                .status(article.getStatus())
                .build();
        return build;
    }

    @Override
    public void deletePhotoAlbumById(Integer albumId) {
        // 查询照片数量
        Integer count = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        if (count > 0) {
            // 若相册下存在照片则逻辑删除相册和照片
            photoAlbumDao.updateById(PhotoAlbum.builder()
                    .id(albumId)
                    .isDelete(CommonConst.TRUE)
                    .build());
            photoDao.update(new Photo(), new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getIsDelete, CommonConst.TRUE)
                    .eq(Photo::getAlbumId, albumId));
        } else {
            // 若相册下不存在照片则直接删除
            photoAlbumDao.deleteById(albumId);
        }
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbums() {
        // 查询相册列表
        List<PhotoAlbum> photoAlbumList = photoAlbumDao.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getStatus, PhotoAlbumStatusEnum.PUBLIC.getStatus())
                .eq(PhotoAlbum::getIsDelete, CommonConst.FALSE)
                .orderByDesc(PhotoAlbum::getId));
        return BeanCopyUtils.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }

}




