
package DD.Android.FixComputer.core;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import roboguice.util.Strings;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static DD.Android.FixComputer.core.Constants.Http.*;
import static com.github.kevinsawicki.http.HttpRequest.get;
import static com.github.kevinsawicki.http.HttpRequest.post;


public class FCService {

    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;


    public static Problem sendProblem(Problem problem, String uuid) {
        Problem result_problem = null;
        HttpRequest request = post(URL_PROBLEMS)
                .part("problem[phone]", problem.getPhone())
                .part("problem[name]", problem.getName())
                .part("problem[address]", problem.getAddress())
                .part("problem[address_plus]", problem.getAddress_plus())
                .part("problem[phone]", problem.getPhone())
                .part("problem[lat]", problem.getLat())
                .part("problem[lng]", problem.getLng())
                .part("problem[uuid]", uuid);


        Log.d("Auth", "response=" + request.code());

        if (request.ok()) {
            String tmp = Strings.toString(request.buffer());
            Log.d("response body:", tmp);
            result_problem = JSON.parseObject(tmp, Problem.class);
        }
        return result_problem;
    }

    public static List<Problem> getProblems(String uuid) throws IOException {
        try {
            HttpRequest request = get(URL_PROBLEMS + "?" + "uuid=" + uuid);
            String body = request.body();
            List<Problem> response = JSON.parseArray(body, Problem.class);
            return response;
        } catch (HttpRequest.HttpRequestException e) {
            throw e.getCause();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static List<Price> getPrices(String uuid) throws IOException {
        try {
            HttpRequest request = get(URL_PRICING + "?" + "uuid=" + uuid);
            String body = request.body();
            List<Price> response = JSON.parseArray(body, Price.class);
            return response;
        } catch (HttpRequest.HttpRequestException e) {
            throw e.getCause();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static Problem getProblem(String problem_id, String uuid) throws IOException {
        try {
            String url = String.format(FORMAT_URL_PROBLEM_WITH_UUID, problem_id, uuid);
            HttpRequest request = get(url);
            String body = request.body();
            Problem respons = JSON.parseObject(body, Problem.class);
            return respons;
        } catch (HttpRequest.HttpRequestException e) {
            throw e.getCause();
        }
    }

    public static Problem updateProblem(String problem_id, String uuid, String status) throws IOException  {
        try {
            String url = String.format(FORMAT_URL_PROBLEM_WITH_UUID_STATUS, problem_id, uuid,status);
            HttpRequest request = post(url)
                    ;
            String body = request.body();
            if(request.ok())
            {
                Problem respons = JSON.parseObject(body, Problem.class);
                return respons;
            }
            return null;
        } catch (HttpRequest.HttpRequestException e) {
            throw e.getCause();
        }
    }
}
