package com.signalfuse.metrics.connection;

import com.signalfuse.metrics.SignalfuseMetricsException;
import com.signalfuse.metrics.endpoint.DataPointReceiverEndpoint;
import com.signalfuse.metrics.protobuf.SignalFuseProtocolBuffers;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpDataPointProtobufReceiverConnectionV2
        extends AbstractHttpDataPointProtobufReceiverConnection {
    public HttpDataPointProtobufReceiverConnectionV2(
            DataPointReceiverEndpoint dataPointEndpoint, int timeoutMs) {
        super(dataPointEndpoint, timeoutMs);
    }

    @Override
    protected String getEndpointForAddDatapoints() {
        return "/v2/datapoint";
    }

    @Override
    protected HttpEntity getEntityForVersion(List<SignalFuseProtocolBuffers.DataPoint> dataPoints) {
        byte[] bodyBytes = SignalFuseProtocolBuffers.DataPointUploadMessage.newBuilder()
                .addAllDatapoints(dataPoints).build().toByteArray();
        return new ByteArrayEntity(bodyBytes, PROTO_TYPE);
    }
    
    @Override
    public Map<String, Boolean> registerMetrics(String auth,
                                                Map<String, SignalFuseProtocolBuffers.MetricType> metricTypes)
            throws SignalfuseMetricsException {
        Map<String, Boolean> res = new HashMap<String, Boolean>();
        for (Map.Entry<String, SignalFuseProtocolBuffers.MetricType> i : metricTypes.entrySet()) {
            res.put(i.getKey(), true);
        }
        return res;
    }
}