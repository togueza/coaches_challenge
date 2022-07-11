package com.code.challenge.service;

import com.code.challenge.exception.NameAlreadyLoadedException;
import com.code.challenge.model.CodeResult;
import com.code.challenge.model.CodeResultEnum;
import com.code.challenge.domain.Code;
import com.code.challenge.exception.NameNotLoadedException;
import com.code.challenge.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <p>Business rules layer related to {@link Code} entity. All the data processing and exceptions will come from here.</p>
 */
@Service
public class
CodeService {

    @Autowired
    private CodeRepository codeRepository;

    /**
     *
     * <p>Store decoded value in the informed side</p>
     *
     * @param id unique identifier of {@link Code} entity
     * @param value Base64 encoded value
     * @param result possible person will be saved
     * @throws NameAlreadyLoadedException
     *
     * @see CodeResultEnum
     */
    @Transactional
    public void save(Long id, String value, CodeResultEnum result) throws NameAlreadyLoadedException {
        Code code = codeRepository.findById(id).orElse(new Code(id));

        if(result.equals(CodeResultEnum.FIND)){
            if(!StringUtils.isEmpty(code.getName())){
                throw new NameAlreadyLoadedException();
            }

            code.setName(new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8));
        } else {
            if(!StringUtils.isEmpty(code.getLastName())){
                throw new NameAlreadyLoadedException();
            }

            code.setLastName(new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8));
        }

        codeRepository.save(code);
    }

    /**
     *
     * <p>Return the comparison result from both LEFT and RIGHT side to the identified {@link Code}</p>
     * <p>In case sides are equal or have different sizes, it will be informed at the returning, but if they have same sizes the differences offset and length will be listed</p>
     *
     * @param id unique identifier of {@link Code} entity
     * @return
     * @throws NameNotLoadedException
     * @see CodeResult
     */
    public CodeResult find(Long id) throws NameNotLoadedException {
        Code code = codeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        CodeResult codeResult = null;

        if(StringUtils.isEmpty(code.getName()) || StringUtils.isEmpty(code.getLastName())){
            throw new NameNotLoadedException();
        }

        if(codeRepository.findById(id).isPresent()){

            codeResult = new CodeResult(CodeResultEnum.FIND, code);
        }

        if(!codeRepository.findById(id).isPresent()){
            codeResult =  new CodeResult(CodeResultEnum.NOT_FIND, code);
        }

        return codeResult;
    }

}
