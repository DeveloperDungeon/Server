package devdungeon.service;

import devdungeon.domain.QuestVO;
import devdungeon.domain.UserVO;
import devdungeon.mapper.QuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {

    private final QuestMapper questMapper;
    private final UserService userService;

    @Override
    public List<QuestVO> getAll() {
        return questMapper.selectAll().stream()
                .map(this::setAuthorDetails)
                .collect(Collectors.toList());
    }

    @Override
    public QuestVO getOne(int id) {
        return setAuthorDetails(questMapper.selectOne(id));
    }

    @Override
    public List<QuestVO> getRecent(int amount) {
        return questMapper.selectRecent(amount).stream()
                .map(this::setAuthorDetails)
                .collect(Collectors.toList());
    }

    @Override
    public int addQuest(QuestVO questVO) {
        return questMapper.insertQuest(questVO);
    }

    private QuestVO setAuthorDetails(QuestVO questVO) {
        UserVO user = userService.getUser(questVO.getAuthor());

        questVO.setAuthorDetails(user);

        return questVO;
    }

    @Override
    public int editQuest(QuestVO questVO){return questMapper.editQuest(questVO);}
}
