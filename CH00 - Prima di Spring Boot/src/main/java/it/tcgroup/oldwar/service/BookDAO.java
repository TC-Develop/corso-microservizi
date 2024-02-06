package it.tcgroup.oldwar.service;

import it.tcgroup.oldwar.service.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    public List<Book> getAll(){
        String sql = "SELECT * FROM BOOK";
        List<Book> result = jdbcTemplate.query(sql, new BookMapper());
        return result;
    }

    public Book getById(UUID id){
        String sql = "SELECT * FROM BOOK WHERE id=:id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        Book book = jdbcTemplate.queryForObject(sql, paramMap,new BookMapper());
        return book;
    }

    public Book save(Book book){
        String sql = "INSERT INTO BOOK " +
                "(id, title, author, code, price, vote, release_date) " +
                "VALUES(:id, :title, :author, :code, :price, :vote, :release_date);";

        book.setId(UUID.randomUUID());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", book.getId());
        paramMap.put("title",book.getTitle());
        paramMap.put("author",book.getAuthor());
        paramMap.put("code",book.getCode());
        paramMap.put("price",book.getPrice());
        paramMap.put("vote",book.getVote());
        paramMap.put("release_date",book.getReleaseDate());

        jdbcTemplate.update(sql,paramMap);
        return book;
    }

    public Book edit(Book book){
        String sql = "UPDATE book " +
                "SET title=:title, author=:author, code=:code, price=:price, vote=:vote, release_date=:release_date " +
                "WHERE id=:id;";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", book.getId());
        paramMap.put("title",book.getTitle());
        paramMap.put("author",book.getAuthor());
        paramMap.put("code",book.getCode());
        paramMap.put("price",book.getPrice());
        paramMap.put("vote",book.getVote());
        paramMap.put("release_date",book.getReleaseDate());

        jdbcTemplate.update(sql,paramMap);
        return book;
    }

    class BookMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getObject("id", UUID.class));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setCode(rs.getString("code"));
            book.setPrice(rs.getBigDecimal("price"));
            book.setVote(rs.getInt("vote"));
            book.setReleaseDate(rs.getDate("release_date"));
            return book;
        }
    }

}
