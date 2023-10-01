package org.bjing.chat.db.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.bjing.chat.db.dto.ChatFindFilter;
import org.bjing.chat.db.entity.Chat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomChatRepository {

    private final EntityManager em;

    public List<Chat> find(ChatFindFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Chat> cq = cb.createQuery(Chat.class);

        Root<Chat> chat = cq.from(Chat.class);

        Integer limit = filter.getMessageLimit();
        Date fromDate = filter.getFromDate();
        Date toDate = filter.getToDate();
        List<Predicate> predicates = new ArrayList<>();
        if (limit != null) {
            Predicate messageLimitPredicate = cb.greaterThan(cb.size(chat.get("messages")), limit);
            predicates.add(messageLimitPredicate);
        }
        if (fromDate != null) {
            Predicate fromDatePredicate = cb.greaterThanOrEqualTo(chat.get("created"), fromDate);
            predicates.add(fromDatePredicate);
        }
        if (toDate != null) {
            Predicate toDatePredicate = cb.lessThanOrEqualTo(chat.get("created"), toDate);
            predicates.add(toDatePredicate);
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Chat> query = em.createQuery(cq);
        return query.getResultList();
    }
}
