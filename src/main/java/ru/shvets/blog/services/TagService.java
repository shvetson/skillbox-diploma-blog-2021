package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.TagInResponse;
import ru.shvets.blog.api.responses.TagResponse;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.Tag;
import ru.shvets.blog.repositories.TagRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagResponse getAllTags(String query) {
        List<TagInResponse> tagInResponseList = new ArrayList<>();
        List<Tag> tagList;

        if (query.trim().length() != 0) {
            tagList = tagRepository.findByNameStartingWithIgnoreCase(query);
            if (tagList.size()==0) return new TagResponse();
        } else {
            tagList = tagRepository.findAll();
        }

        double scale = Math.pow(10, 2); // определение разрядности для вывода double
        int countTags = (int) tagRepository.count(); // расчет общего количества всех постов в базе

        tagList.forEach(tag -> {
            int countPostsWithTag = (int) tag.getPostList().stream().filter(item -> item.getModerationStatus() == ModerationStatus.ACCEPTED).count(); // подсчет количества постов с данным тэгом и со статусом ACCEPTED

            if (countPostsWithTag > 0) {
                double weight = countPostsWithTag / (double) countTags;
                tagInResponseList.add(new TagInResponse(tag.getName(), countPostsWithTag, Math.ceil(weight * scale) / scale, 0));
            }
        });

        double k = 1 / tagInResponseList.stream().map(TagInResponse::getCountWeight).max(Double::compareTo).get(); // расчет коэффициент для нормализации

        tagInResponseList.forEach(item -> {
            double normWeight = Math.ceil((item.getCountWeight() * k) * scale) / scale; // расчет нормированного веса
            item.setWeight(normWeight); // присвоение нормированного веса для каждого тэга
        });

        return new TagResponse(tagInResponseList.stream().sorted(Comparator.comparingDouble(TagInResponse::getWeight)).collect(Collectors.toList()));
    }
}