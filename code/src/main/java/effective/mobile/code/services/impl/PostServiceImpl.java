package effective.mobile.code.services.impl;

import effective.mobile.code.dto.request.PostRequest;
import effective.mobile.code.entities.Post;
import effective.mobile.code.exceptions.NotFindEntity;
import effective.mobile.code.repositories.PostRepository;
import effective.mobile.code.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final String url = new File("").getAbsolutePath();
    private final Path rootLocation = Paths.get(url + "/images");

    @Override
    public Post getPost(Long id) {
        return postRepository.getById(id);
    }

    @Override
    public Post savePost(PostRequest postRequest) {
        String path = saveImage(postRequest);

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .text(postRequest.getText())
                .imageURL(path)
                .user(postRequest.getUser())
                .build();

        return postRepository.save(post);
    }

    @Override
    public Post partialUpdate(Long id, PostRequest postRequest) throws NotFindEntity {
        if (postRepository.existsById(id)) {
            Post post = postRepository.getById(id);

            if (postRequest.getTitle() != null) {
                post.setTitle(postRequest.getTitle());
            }

            if (postRequest.getText() != null) {
                post.setText(postRequest.getText());
            }

            if (postRequest.getImage() != null) {
                post.setImageURL(saveImage(postRequest));
            }

            return postRepository.save(post);
        } else {
            throw new NotFindEntity("post");
        }
    }

    @Override
    public void deletePost(Long id) throws NotFindEntity {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new NotFindEntity("post");
        }
    }

    private String saveImage(PostRequest postRequest) {
        MultipartFile image = postRequest.getImage();

        /* TODO Сделать проверку на принадлежность к изображению
        *   Форматы jpg/png */

        /* TODO Сделать проверку дупликат файла
        *   если есть, то сделать рандомную приписку */

        Path destination = rootLocation.resolve(
                Paths.get(image.getOriginalFilename())
        ).normalize().toAbsolutePath();

        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return destination.toString();
    }
}
