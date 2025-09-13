package com.ancilar.ancilartask.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ancilar.ancilartask.entities.Comment;
import com.ancilar.ancilartask.entities.Post;
import com.ancilar.ancilartask.repositories.CommentRepository;
import com.ancilar.ancilartask.repositories.PostRepository;

@Controller
@RequestMapping("/posts")
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.ok("Post Create3d Successfully!!!!");
    }

    // get all the posts

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // updating posts title or content

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePosts(@PathVariable String id, @RequestBody String updatedData) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(404).body("Post Not Found");
        } else {
            Post post = optionalPost.get();
            post.setTitle(updatedData);
            post.setContent(updatedData);
            postRepository.save(post);
            return ResponseEntity.ok("Post Updated Successfully!!!!");

        }

    }

    // Delete post and all its comments

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Add comment to the post
    @PostMapping("/{postID}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable String postId, @RequestBody Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        comment.setPost(optionalPost.get());
        Comment saveComment = commentRepository.save(comment);

        return ResponseEntity.ok().body(saveComment);

    }

    // Get all comments for a post
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable String postId) {
        if (!postRepository.existsById(postId)) {
            return ResponseEntity.notFound().build();
        }
        List<Comment> comments = commentRepository.findbyPostID(postId);
        return ResponseEntity.ok(comments);
    }

    // delete a specific comment

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        if (!commentRepository.existsById(commentId)) {
            return ResponseEntity.notFound().build();
        }
        commentRepository.deleteById(commentId);
        return ResponseEntity.ok().build();
    }

}
