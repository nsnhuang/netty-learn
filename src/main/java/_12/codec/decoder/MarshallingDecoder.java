package _12.codec.decoder;

import _12.codec.MarshallingCodeCFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author huang
 * @version v1.0
 * @date 2019-10-22 4:54 PM
 */
public class MarshallingDecoder {

    Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        this.unmarshaller = MarshallingCodeCFactory.buildUnMarshalling();
    }

    public Object decode(ByteBuf in) throws IOException, ClassNotFoundException {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }

    }
}