package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;
import com.chat.demo.model.SenderType;

public interface SenderTypeService {

    SenderType save(SenderType senderType);

    Optional<SenderType> findById(Long id);

    Optional<SenderType> findByName(String name);

    List<SenderType> findAll();

    void delete(Long id);
}
