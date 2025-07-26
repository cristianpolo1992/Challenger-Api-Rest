package com.alurachallengertopico.topicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    // Create a new topic
    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        return topicRepository.save(topic);
    }

    // Get all topics
    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Get a specific topic by ID
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a topic
    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Optional<Topic> existingTopic = topicRepository.findById(id);
        if (existingTopic.isPresent()) {
            Topic topic = existingTopic.get();
            topic.setTitle(updatedTopic.getTitle());
            topic.setDescription(updatedTopic.getDescription());
            return ResponseEntity.ok(topicRepository.save(topic));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a topic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
