package com.delivery.product.web.mappers;

import com.delivery.product.domain.Tag;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TagMapper {

    public Set<Tag> toTag(Set<String> tags){
        if(tags != null){
            return tags.stream()
                    .map(s -> Tag.builder().value(s).build())
                    .collect(Collectors.toSet());
        }
        return null;
    }

    public Set<String> toTagString(Set<Tag> tags){
        if(tags != null){
            return tags.stream()
                    .map(tag -> tag.getValue())
                    .collect(Collectors.toSet());
        }
        return null;
    }

}
