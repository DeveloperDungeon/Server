package devdungeon.controller;

import devdungeon.annotation.CertifyAnnotation;
import devdungeon.domain.PageVO;
import devdungeon.domain.QuestVO;
import devdungeon.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quest")
public class QuestController {

    private final QuestService questService;
    private final HttpSession session;

    @GetMapping
    public String getQuestList(Model model, @RequestParam(value = "page", required = false,
            defaultValue = "1") int page) {

        PageVO pageVO = new PageVO(page, questService.getTotalQuestNum());
        model.addAttribute("pageInfo", pageVO);
        model.addAttribute("questList",
                questService.getQuestWithPage(PageVO.QUESTS_PER_PAGE, pageVO.getOffset()));
        return "quest/list";
    }

    @GetMapping("/get/{id}")
    public String getQuest(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("quest", questService.getOne(id));
        return "quest/get";
    }

    @GetMapping("/write")
    @CertifyAnnotation
    public String getQuestWrite() {
        return "quest/write";
    }

    @PostMapping("/write")
    @CertifyAnnotation
    public String postQuestWrite(@RequestBody QuestVO questVO) {
        questVO.setAuthor((String) session.getAttribute("user"));
        questService.addQuest(questVO);
        return "redirect:/quest";
    }

    @GetMapping("/edit")
    @CertifyAnnotation
    public String getQuestEdit() {
        return "questEdit";
    }

    @PutMapping("/edit")
    @CertifyAnnotation
    public String putQuestEdit(@RequestBody QuestVO questVO) {
        questService.editQuest(questVO);
        return "questEdit";
    }
}
