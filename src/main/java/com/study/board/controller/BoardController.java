package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board,Model model){
        boardService.write(board);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable) {

        // 서비스 계층에서 페이징된 게시물 리스트를 가져온다.
        Page<Board> list = boardService.boardList(pageable);
        // 현재 페이지 번호를 계산 (0-based에서 1-based로 변환)
        int nowPage = list.getPageable().getPageNumber() + 1;
        // 페이지 네비게이션의 시작 페이지를 계산한다.
        int startPage = Math.max(nowPage - 4, 1);
        // 페이지 네비게이션의 끝 페이지를 계산한다.
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        //모델에 데이터 추가
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8090/board/view?id=1
    public String boardView(Model model, @RequestParam("id") Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";

    }
}
