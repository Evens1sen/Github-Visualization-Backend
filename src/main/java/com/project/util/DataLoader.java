package com.project.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.entity.Commit;
import com.project.entity.Issue;
import com.project.entity.Releases;
import com.project.service.CommitService;
import com.project.service.IssueService;
import com.project.service.ReleasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class is the tool class to fetch and store GitHub data.
 * It first uses GitHub RESTful api to fetch json
 * Then use Gson to parse json into entity object
 * Finally store the entity into database
 */
@Component
public class DataLoader {

    private static CommitService commitService;

    private static IssueService issueService;

    private static ReleasesService releasesService;

    @Autowired
    public void setCommitService(CommitService commitService) {
        DataLoader.commitService = commitService;
    }

    @Autowired
    public void setIssueService(IssueService issueService) {
        DataLoader.issueService = issueService;
    }

    @Autowired
    public void setReleaseService(ReleasesService releasesService) {
        DataLoader.releasesService = releasesService;
    }

    public static void loadData(String user, String repo) {
        loadCommit(user, repo);
        loadIssue(user, repo);
        loadRelease(user, repo);
    }

    private static String request(String target) {
        HttpURLConnection conn = null;
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(target);
            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert conn != null;
            conn.disconnect();
        }

        return responseContent.toString();
    }

    private static void loadCommit(String user, String repo) {
        commitService.remove(new QueryWrapper<>());

        int pageNum = 1;
        String response = "";
        while (true) {
            String target = String.format("https://api.github.com/repos/%s/%s/commits?page=%d", user, repo, pageNum);
            response = request(target);
            if (response.equals("[]")) {
                break;
            }

            Gson gson = new Gson();
            JsonArray res = gson.fromJson(response, JsonArray.class);
            for (int i = 0; i < res.size(); i++) {
                String sha = String.valueOf(res.get(i).getAsJsonObject().get("sha")).substring(0, 8);
                String author = String.valueOf(res.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("name"));
                String createdAt = String.valueOf(res.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date"));
                createdAt = createdAt.equals("null") ? null : createdAt.substring(1, createdAt.length() - 2);
                Commit commit = new Commit();
                commit.setSha(sha);
                commit.setAuthor(author);
                commit.setCreatedAt(LocalDateTime.parse(createdAt));
                commitService.save(commit);
            }

            pageNum++;
        }
    }

    private static void loadIssue(String user, String repo) {
        issueService.remove(new QueryWrapper<>());

        int pageNum = 1;
        String response = "";
        while (true) {
            String target = String.format("https://api.github.com/repos/%s/%s/issues?state=all&page=%d", user, repo, pageNum);
            response = request(target);
            if (response.equals("[]")) {
                break;
            }

            Gson gson = new Gson();
            JsonArray res = gson.fromJson(response, JsonArray.class);
            for (int i = 0; i < res.size(); i++) {
                String state = String.valueOf(res.get(i).getAsJsonObject().get("state"));
                String createdAt = String.valueOf(res.get(i).getAsJsonObject().get("created_at"));
                String updatedAt = String.valueOf(res.get(i).getAsJsonObject().get("updated_at"));
                String closedAt = String.valueOf(res.get(i).getAsJsonObject().get("closed_at"));
                String title = String.valueOf(res.get(i).getAsJsonObject().get("title"));
                String body = String.valueOf(res.get(i).getAsJsonObject().get("body"));

                state = state.substring(1, state.length() - 1);
                createdAt = createdAt.equals("null") ? null : createdAt.substring(1, createdAt.length() - 2);
                updatedAt = updatedAt.equals("null") ? null : updatedAt.substring(1, updatedAt.length() - 2);
                closedAt = closedAt.equals("null") ? null : closedAt.substring(1, closedAt.length() - 2);
                title = title.equals("null") ? null : title.substring(1, title.length() - 2);
                body = body.equals("null") ? null : body.substring(1, body.length() - 2);
                if(body.length()>50){
                    body = body.substring(1,50);
                }

                System.out.println(state);
                System.out.println(createdAt);
                System.out.println(updatedAt);
                System.out.println(closedAt);

                Issue issue = new Issue();
                issue.setState(state);
                if (createdAt != null) {
                    issue.setCreatedAt(LocalDateTime.parse(createdAt));
                }
                if (updatedAt != null) {
                    issue.setUpdatedAt(LocalDateTime.parse(updatedAt));
                    issue.setIssueTime(Math.toIntExact(Duration.between(issue.getCreatedAt(), issue.getUpdatedAt()).toSeconds()));
                }
                if (closedAt != null) {
                    issue.setClosedAt(LocalDateTime.parse(closedAt));
                    issue.setIssueTime(Math.toIntExact(Duration.between(issue.getCreatedAt(), issue.getClosedAt()).toSeconds()));
                }
                issue.setTitle(title);
                if (body != null) {
                    issue.setBody(body);
                }

                issueService.save(issue);
            }

            pageNum++;
        }
    }

    private static void loadRelease(String user, String repo) {
        releasesService.remove(new QueryWrapper<>());

        int pageNum = 1;
        String response = "";
        while (true) {
            String target = String.format("https://api.github.com/repos/%s/%s/releases?&page=%d", user, repo, pageNum);
            response = request(target);
            if (response.equals("[]")) {
                break;
            }

            Gson gson = new Gson();
            JsonArray res = gson.fromJson(response, JsonArray.class);
            for (int i = 0; i < res.size(); i++) {
                String createdAt = String.valueOf(res.get(i).getAsJsonObject().get("created_at"));
                String publishedAt = String.valueOf(res.get(i).getAsJsonObject().get("published_at"));
                createdAt = createdAt.equals("null") ? null : createdAt.substring(1, createdAt.length() - 2);
                publishedAt = publishedAt.equals("null") ? null : publishedAt.substring(1, publishedAt.length() - 2);

                System.out.println(createdAt);
                System.out.println(publishedAt);

                Releases release = new Releases();
                if (createdAt != null) {
                    release.setCreatedAt(LocalDateTime.parse(createdAt));
                }
                if (publishedAt != null) {
                    release.setPublishedAt(LocalDateTime.parse(publishedAt));
                }
                releasesService.save(release);
            }

            pageNum++;
        }
    }

}
