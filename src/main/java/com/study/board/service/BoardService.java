package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    //글작성 처리
    public void write(Board board){
        boardRepository.save(board);
    }
    //게시글리스트 처리
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable); //Pageable 객체를 사용하여 페이징된
        // 게시물 리스트를 데이터베이스에서 가져온다.
    }
    //특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }
    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}

