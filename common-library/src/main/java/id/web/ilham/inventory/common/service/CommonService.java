package id.web.ilham.inventory.common.service;

import id.web.ilham.inventory.common.model.CommonRequest;
import id.web.ilham.inventory.common.model.CommonResponse;
import id.web.ilham.inventory.common.model.ResponseMessage;

public interface CommonService<RequestT extends CommonRequest, ResponseT extends CommonResponse> {
    ResponseMessage<ResponseT> execute(RequestT request);

}
