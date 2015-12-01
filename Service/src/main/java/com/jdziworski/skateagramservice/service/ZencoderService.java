package com.jdziworski.skateagramservice.service;

import com.brightcove.zencoder.client.ZencoderClient;
import com.brightcove.zencoder.client.ZencoderClientException;
import com.brightcove.zencoder.client.model.ContainerFormat;
import com.brightcove.zencoder.client.model.State;
import com.brightcove.zencoder.client.request.ZencoderCreateJobRequest;
import com.brightcove.zencoder.client.request.ZencoderOutput;
import com.brightcove.zencoder.client.response.ZencoderCreateJobResponse;
import com.brightcove.zencoder.client.response.ZencoderJobDetail;
import com.brightcove.zencoder.client.response.ZencoderMediaFile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuba on 01.12.2015.
 */
@Service
public class ZencoderService {

    private ZencoderClient client;

    @PostConstruct
    public void init() {
        client = new ZencoderClient("6cb74a27c037a9e117a909cebc0f7c15");
    }

    public String encode(String url) {
        ZencoderCreateJobRequest job = new ZencoderCreateJobRequest();
        job.setInput(url);
        List<ZencoderOutput> outputs = new ArrayList<ZencoderOutput>();
        ZencoderOutput output1 = new ZencoderOutput();
        output1.setFormat(ContainerFormat.MP4);
        outputs.add(output1);

        job.setOutputs(outputs);
        ZencoderCreateJobResponse response = null;
        try {
            response = client.createZencoderJob(job);
            String jobID = response.getId();
            ZencoderMediaFile outpuFile = null;
            while(client.getZencoderJob(jobID).getState() != State.FINISHED) {
                if(client.getZencoderJob(jobID).getState() == State.FAILED) return "";
                Thread.sleep(100);
            }
            return client.getZencoderJob(jobID).getOutputMediaFiles().get(0).getUrl();
        } catch (ZencoderClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
