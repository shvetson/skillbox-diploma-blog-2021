package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.TagResponse;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.Tag;
import ru.shvets.blog.models.Tag2Post;
import ru.shvets.blog.repositories.Tag2PostRepository;
import ru.shvets.blog.repositories.TagRepository;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;

    public Map<String, Object> getAllTags(String query) {
        Map<String, Object> response = new HashMap<>();
        List<TagResponse> tagResponseList = new ArrayList<>();
        List<Tag> tagList;

        if (query == null) {
            tagList = tagRepository.findAll();
        } else {
            tagList = tagRepository.findByNameStartingWithIgnoreCase(query);
        }

        if (tagList.isEmpty()) {
            response.put("tags", new TagResponse[] {});
            return response;
        }

        double scale = Math.pow(10, 2); // определение разрядности для вывода double
        int countTags = (int) tagRepository.count(); // расчет общего количества всех постов в базе

        tagList.forEach(tag -> {
            int countPostsWithTag = (int) tag.getPostList().stream().
                    filter(item -> item.getModerationStatus() == ModerationStatus.ACCEPTED).
                    count(); // подсчет количества постов с данным тэгом и со статусом ACCEPTED

            if (countPostsWithTag > 0) {
                double weight = countPostsWithTag / (double) countTags;
                tagResponseList.add(new TagResponse(tag.getName(), countPostsWithTag, Math.ceil(weight * scale) / scale, 0));
            }
        });

        double k = 1 / tagResponseList.stream().
                map(TagResponse::getCountWeight).
                max(Double::compareTo).get(); // расчет коэффициент для нормализации

        tagResponseList.forEach(item -> {
            double normWeight = Math.ceil((item.getCountWeight() * k) * scale) / scale; // расчет нормированного веса
            item.setWeight(normWeight); // присвоение нормированного веса для каждого тэга
        });

        response.put("tags", tagResponseList.stream().
                sorted(Comparator.comparingDouble(TagResponse::getWeight)).
                collect(Collectors.toList()));

        return response;
    }

    public Tag findTagByName(String name){
        return tagRepository.findTagByName(name);
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public Tag2Post save(Tag2Post tag2Post){
        return tag2PostRepository.save(tag2Post);
    }
}