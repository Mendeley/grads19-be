package grads19.batch;

import grads19.mappers.ConferenceMapper;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class ConferenceReader extends JdbcPagingItemReader<ConferenceDTO> {

    private static final String SELECT_CLAUSE = "SELECT id, name, date_time, city, description, topic";
    private static final String FROM_CLAUSE = "FROM conferences";

    public ConferenceReader(ConferenceMapper conferenceMapper,
                            DataSource mysqlDataSource,
                            @Value("${spring.batch.chunk-size}") Integer chunkSize) {
        setDataSource(mysqlDataSource);
        setPageSize(chunkSize);
        setRowMapper(conferenceMapper);
        setQueryProvider(getQueryProvider());
    }

    private PagingQueryProvider getQueryProvider() {
        final var queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause(SELECT_CLAUSE);
        queryProvider.setFromClause(FROM_CLAUSE);
        queryProvider.setSortKeys(Map.of("id", Order.ASCENDING));

        return queryProvider;
    }
}