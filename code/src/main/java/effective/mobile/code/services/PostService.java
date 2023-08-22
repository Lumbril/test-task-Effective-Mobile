package effective.mobile.code.services;

import effective.mobile.code.dto.request.PostRequest;
import effective.mobile.code.entities.Post;
import effective.mobile.code.exceptions.NotFindEntity;

public interface PostService {
    Post getPost(Long id);
    Post savePost(PostRequest post);
    Post partialUpdate(Long id, PostRequest postUpdateRequest) throws NotFindEntity;
    void deletePost(Long id) throws NotFindEntity;
}
