package org.mflix.forecast.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.mflix.forecast.view.CountryView;
import org.mflix.forecast.view.DirectorView;
import org.mflix.forecast.view.MovieView;
import org.mflix.forecast.view.StarringView;
import org.mflix.forecast.view.TagView;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DoubanComponent {
    public void extract(String url, MovieView movieView) {
        var content = getContent(url);
        var info = content.getElementById("info").text();
        var infoMatcher = Pattern.compile("(\\S*):(\\s\\S*\\s\\/|\\s\\S*\\s)*").matcher(info);
        while (infoMatcher.find()) {
            var infoString = infoMatcher.group().trim();
            if (infoString.startsWith("导演")) {
                getDirectors(infoString, movieView);
            } else if (infoString.startsWith("主演")) {
                getStarrings(infoString, movieView);
            } else if (infoString.startsWith("类型")) {
                getTags(infoString, movieView);
            } else if (infoString.startsWith("制片国家/地区")) {
                getCountries(infoString, movieView);
            } else if (infoString.startsWith("上映日期")) {
                getReleaseDate(infoString, movieView);
            }
        }
        getIntroduction(content, movieView);
        getName(content, movieView);
        getPosterUrl(content, movieView);
        getScore(content, movieView);
    }

    private void getDirectors(String infoString, MovieView movieView) {
        var directors = infoString.split(": ");
        var directorViewSet = Arrays.asList(directors[1].split(" / ")).stream()
                .map(director -> new DirectorView(director)).collect(Collectors.toSet());
        movieView.setDirectorViewSet(directorViewSet);
    }

    private void getStarrings(String infoString, MovieView movieView) {
        var starrings = infoString.split(": ");
        var starringViewSet = Arrays.asList(starrings[1].split(" / ")).stream()
                .map(starring -> new StarringView(starring)).collect(Collectors.toSet());
        movieView.setStarringViewSet(starringViewSet);
    }

    private void getTags(String infoString, MovieView movieView) {
        var tags = infoString.split(": ");
        var tagsViewSet = Arrays.asList(tags[1].split(" / ")).stream().map(tag -> new TagView(tag))
                .collect(Collectors.toSet());
        movieView.setTagViewSet(tagsViewSet);
    }

    private void getCountries(String infoString, MovieView movieView) {
        var countries = infoString.split(": ");
        var countryViewSet = Arrays.asList(countries[1].split(" / ")).stream().map(country -> new CountryView(country))
                .collect(Collectors.toSet());
        movieView.setCountryViewSet(countryViewSet);
    }

    private void getReleaseDate(String infoString, MovieView movieView) {
        var releaseDates = infoString.split(": ");
        var date = Arrays.asList(releaseDates[1].split(" / ")).stream().map((releaseDate) -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
            } catch (ParseException e) {
                return new Date();
            }
        }).min((a, b) -> {
            return (int) (a.getTime() - b.getTime());
        }).orElseThrow();
        movieView.setReleaseDate(date);
    }

    private void getIntroduction(Element content, MovieView movieView) {
        movieView.setIntroduction(
                content.getElementsByClass("related-info").get(0).getElementsByClass("indent").get(0).text());
    }

    private void getName(Element content, MovieView movieView) {
        var name = content.getElementsByTag("h1").get(0).getElementsByTag("span").get(0).text();
        var nameMatcher = Pattern.compile("^\\S*|\\s.*").matcher(name);
        if (nameMatcher.find()) {
            movieView.setChineseName(nameMatcher.group());

        }
        if (nameMatcher.find()) {
            movieView.setOriginName(nameMatcher.group().trim());
        }
    }

    private void getPosterUrl(Element content, MovieView movieView) {
        movieView.setPosterUrl(content.getElementById("mainpic").getElementsByTag("img").get(0).attr("src"));
    }

    private void getScore(Element content, MovieView movieView) {
        var score = content.getElementById("interest_sectl").getElementsByTag("strong").get(0).text();
        if (!score.equals("")) {
            movieView.setScore(Double.parseDouble(score));
        }
    }

    private Element getContent(String url) {
        var html = new RestTemplate().getForObject(url, String.class);
        var document = Jsoup.parse(html);
        return document.getElementById("content");
    }
}