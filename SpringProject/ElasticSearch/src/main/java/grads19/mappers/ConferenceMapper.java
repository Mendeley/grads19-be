package grads19.mappers;

import grads19.batch.ConferenceDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import static grads19.batch.ConferenceDTO.ConferenceDTOBuilder.aConferenceDTO;

@Component
public class ConferenceMapper implements RowMapper<ConferenceDTO> {

    public Instant DateConverter(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");

        try {
            Date date = formatter.parse(dateTime);
            Instant dateTimeInstant = date.toInstant();
            return dateTimeInstant;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public ConferenceDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Long id = resultSet.getLong("id");
        String dateTime = resultSet.getString("date_time");
        String name = resultSet.getString("name");
        String city = resultSet.getString("city");
        String description = resultSet.getString("description");
        String topic = resultSet.getString("topic");



        return aConferenceDTO()
                .withId(id)
                .withDateTime(DateConverter(dateTime))
                .withName(name)
                .withCity(city)
                .withDescription(description)
                .withTopic(topic)
                .build();

    }


}