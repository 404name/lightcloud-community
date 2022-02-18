package com.juelunn.lightcloud.module.blog.contorller;

import com.juelunn.common.annotation.OptLog;
import com.juelunn.common.dto.PhotoAlbumBackDTO;
import com.juelunn.common.dto.PhotoAlbumDTO;
import com.juelunn.common.enums.FilePathEnum;
import com.juelunn.lightcloud.module.blog.service.PhotoAlbumService;

import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.vo.PhotoAlbumVO;
import com.juelunn.common.vo.Result;
import com.juelunn.common.constant.OptTypeConst;
import com.juelunn.oss.context.UploadStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 相册控制器
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/04
 */
@Api(tags = "相册模块")
@RestController
public class PhotoAlbumController {
    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    @Autowired
    private PhotoAlbumService photoAlbumService;

    /**
     * 上传相册封面
     *
     * @param file 文件
     * @return {@link Result<String>} 相册封面地址
     */
    @ApiOperation(value = "上传相册封面")
    @ApiImplicitParam(name = "file", value = "相册封面", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/photos/albums/cover")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    /**
     * 保存或更新相册
     *
     * @param photoAlbumVO 相册信息
     * @return {@link Result<>}
     */
    @OptLog(optType = OptTypeConst.SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新相册")
    @PostMapping("/admin/photos/albums")
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.ok();
    }

    /**
     * 查看后台相册列表
     *
     * @param condition 条件
     * @return {@link Result< PhotoAlbumBackDTO >} 相册列表
     */
    @ApiOperation(value = "查看后台相册列表")
    @GetMapping("/admin/photos/albums")
    public Result<PageResult<PhotoAlbumBackDTO>> listPhotoAlbumBacks(ConditionVO condition) {
        return Result.ok(photoAlbumService.listPhotoAlbumBacks(condition));
    }

    /**
     * 获取后台相册列表信息
     *
     * @return {@link Result< PhotoAlbumDTO >} 相册列表信息
     */
    @ApiOperation(value = "获取后台相册列表信息")
    @GetMapping("/admin/photos/albums/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.ok(photoAlbumService.listPhotoAlbumBackInfos());
    }

    /**
     * 根据id获取后台相册信息
     *
     * @param albumId 相册id
     * @return {@link Result}相册信息
     */
    @ApiOperation(value = "根据id获取后台相册信息")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @GetMapping("/admin/photos/albums/{albumId}/info")
    public Result<PhotoAlbumBackDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoAlbumService.getPhotoAlbumBackById(albumId));
    }

    /**
     * 根据id删除相册
     *
     * @param albumId 相册id
     * @return {@link Result}
     */
    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation(value = "根据id删除相册")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @DeleteMapping("/admin/photos/albums/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.ok();
    }

    /**
     * 获取相册列表
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表
     */
    @ApiOperation(value = "获取相册列表")
    @GetMapping("/photos/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.ok(photoAlbumService.listPhotoAlbums());
    }

}
