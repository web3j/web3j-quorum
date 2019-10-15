package org.web3j.quorum.methods.response.raft;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

public class RaftCluster extends Response<List<RaftPeer>>{
	public Optional<List<RaftPeer>> getCluster() {
		return Optional.ofNullable(getResult());
	}
	
	public static class ResponseDeserializer extends JsonDeserializer<List<RaftPeer>> {
		private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();
		
		@Override
		public List<RaftPeer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
			if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
				Iterator<RaftPeer> it = objectReader.readValues(jsonParser, RaftPeer.class);
				Iterable<RaftPeer> iterable = () -> it;
				return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
			} else {
				return null;
			}
		}
	}
}
