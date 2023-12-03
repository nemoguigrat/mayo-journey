package com.example.mayo.journey.repository.jdbc.specification;

import com.example.mayo.journey.domain.jdbc.DocumentIndex;
import com.example.mayo.journey.domain.jdbc.DocumentShort;
import com.example.mayo.journey.service.dto.journey.DocumentShortFilter;
import com.example.mayo.journey.support.DocumentStatus;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentShortSpec {

    public static Specification<DocumentShort> documentShortFilter(MayoUserDetails user, DocumentShortFilter filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<DocumentShort, DocumentIndex> join = root.join(DocumentShort.Fields.documentIndex, JoinType.LEFT);

            if (filter.getStatus() != null) {
                Predicate status = cb.equal(join.get(DocumentIndex.Fields.status), filter.getStatus());
                predicates.add(status);
            }

            if (filter.getStatus() == DocumentStatus.DRAFT) {
                Path<Long> userId = join.get(DocumentIndex.Fields.user).get("id");
                Predicate userEquals = cb.equal(userId, user.getId());
                predicates.add(userEquals);
            }

            if (filter.getThemeId() != null) {
                Path<Long> themeId = join.get(DocumentIndex.Fields.theme).get("id");
                Predicate theme = cb.equal(themeId, filter.getThemeId());
                predicates.add(theme);

            }
            // нужно для count-query в запросах с пагинацией
            if (Long.class != query.getResultType()) {
                Fetch<Object, Object> index = root.fetch(DocumentShort.Fields.documentIndex);
                index.fetch(DocumentIndex.Fields.placemarks).fetch("attachment");
                index.fetch(DocumentIndex.Fields.theme);
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
