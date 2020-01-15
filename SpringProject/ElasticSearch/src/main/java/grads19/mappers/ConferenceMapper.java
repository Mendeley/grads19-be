package grads19.mappers;

import grads19.batch.ConferenceDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static grads19.batch.ConferenceDTO.ConferenceDTOBuilder.aConferenceDTO;

@Component
public class ConferenceMapper implements RowMapper<ConferenceDTO> {

    @Override
    public ConferenceDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String city = resultSet.getString("city");
        String description = resultSet.getString("description");
        String topic = resultSet.getString("topic");

        return aConferenceDTO()
                .withId(id)
                .withName(name)
                .withCity(city)
                .withDescription(description)
                .withTopic(topic)
                .build();
    }
}