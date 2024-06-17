package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){
        boardService.write(board);
        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        //서비스에서 게시글 리스트를 조회하여 모델에 추가
        model.addAttribute("list",boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8090/board/view?id=1
    public String boardView(Model model, @RequestParam("id") Integer id){
        //서비스에서 특정 게시글을 조회하여 모델에 추가
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }
}
