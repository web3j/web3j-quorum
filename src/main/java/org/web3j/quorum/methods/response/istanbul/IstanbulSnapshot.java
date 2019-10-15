package org.web3j.quorum.methods.response.istanbul;

import java.io.IOException;
import java.util.Optional;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

public class IstanbulSnapshot extends Response<Snapshot> {
	
	public Optional<Snapshot> getSnapshot() {
		return Optional.ofNullable(getResult());
	}
	
	public static class ResponseDeserialiser extends JsonDeserializer<Snapshot> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Snapshot deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Snapshot.class);
            } else {
                return null; // null is wrapped by Optional in above getter
            }
        }
    }
}
