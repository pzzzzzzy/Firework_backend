package org.example.service.impl;

import org.example.entity.StudyResource;
import org.example.entity.Version;
import org.example.model.FileListResponse;
import org.example.repository.CourseRepository;
import org.example.repository.StudyResourceRepository;
import org.example.repository.VersionRepository;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private StudyResourceRepository studyResourceRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private VersionRepository versionRepository;

    @Override
    public FileListResponse getCourseFiles(Long courseId) {
        // 检查课程是否存在
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        // 获取课程的所有文件
        List<StudyResource> resources = studyResourceRepository.findByCourse_Id(courseId);

        // 转换为响应格式
        FileListResponse response = new FileListResponse();
        response.setFiles(resources.stream()
                .map(this::convertToFileInfo)
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public FileListResponse searchFiles(String keyword) {
        // 验证关键字不为空
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search keyword cannot be empty");
        }

        // 搜索文件
        List<StudyResource> resources = studyResourceRepository.findByNameContainingIgnoreCase(keyword.trim());

        // 转换为响应格式
        FileListResponse response = new FileListResponse();
        response.setFiles(resources.stream()
                .map(this::convertToFileInfo)
                .collect(Collectors.toList()));

        return response;
    }

    private FileListResponse.FileInfo convertToFileInfo(StudyResource resource) {
        FileListResponse.FileInfo fileInfo = new FileListResponse.FileInfo();
        fileInfo.setId(String.valueOf(resource.getId()));
        fileInfo.setName(resource.getName());
        fileInfo.setSize(resource.getFileSize());
        fileInfo.setType(resource.getFileType());
        fileInfo.setUploadTime(resource.getUploadTime()
                .format(DateTimeFormatter.ISO_DATE_TIME));
        return fileInfo;
    }

	@Override
	public StudyResource previewFileVersion(String fileId, String versionId) {
		 StudyResource resource = studyResourceRepository.findById(Integer.parseInt(fileId))
		            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

		return resource;
	}

	@Override
	public String deleteFileVersion(String fileId, String versionId) {
		 // 根据versionId查找Version记录
	    Optional<Version> versionOptional = versionRepository.findById(versionId);

	    if (!versionOptional.isPresent()) {
	        throw new IllegalArgumentException("Version with ID " + versionId + " not found.");
	    }
	    Version version = versionOptional.get();
	    // 获取文件路径并删除文件
	    String filePath = version.getFilePath();
	    try {
	        Path path = Paths.get(filePath);
	        Files.deleteIfExists(path); // 删除文件

	        // 删除数据库中的版本记录
	        versionRepository.delete(version);

	        // 返回成功信息
	        return "File version deleted successfully.";
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to delete the file from path: " + filePath, e);
	    }
	}

	@Override
	public byte[] downloadFileVersion(String fileId, String versionId) {
		 Optional<Version> versionOptional = versionRepository.findById(versionId);

		    if (!versionOptional.isPresent()) {
		        throw new IllegalArgumentException("Version with ID " + versionId + " not found.");
		    }

		    Version version = versionOptional.get();
		    String filePath = version.getFilePath();

		    // 读取文件并返回byte[]
		    try {
		        Path path = Paths.get(filePath);
		        return Files.readAllBytes(path);
		    } catch (IOException e) {
		        throw new RuntimeException("Failed to read the file from path: " + filePath, e);
		    }
	}

	@Override
	public List<Version> getFileVersions(String fileId) {
		StudyResource resource = studyResourceRepository.findById(Integer.parseInt(fileId))
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));
		String filename=resource.getName();
		List<StudyResource> filelist=studyResourceRepository.findByName(filename);
		if(filelist==null) {
			return Collections.emptyList();
		}
		List<Version> versions = filelist.stream()
	            .map(studyResource -> convertToVersion(studyResource))
	            .collect(Collectors.toList());

	    return versions;
	}
	
	private Version convertToVersion(StudyResource studyResource) {
		Optional<Version> optionalVersion=versionRepository.findById(studyResource.getId().toString());
		if (optionalVersion.isPresent()) {
		    Version version = optionalVersion.get();
		    return version;
		    // 处理version
		} else {
		    return null;
		}
	    
	}
	@Override
	public Version createFileVersion(String fileId, MultipartFile file, String description) {
		// TODO Auto-generated method stub
		return null;
	}
} 