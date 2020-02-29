package org.mflix.forecast.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
        int directorsIndex = info.indexOf("导演:", 0);
        int scriptwriterIndex = info.indexOf("编剧:", directorsIndex);
        getDirectors(info, directorsIndex, scriptwriterIndex, movieView);
        int starringsIndex = info.indexOf("主演:", scriptwriterIndex);
        int tagsIndex = info.indexOf("类型:", starringsIndex);
        getStarrings(info, starringsIndex, tagsIndex, movieView);
        int officialWebsiteIndex = info.indexOf("官方网站:", tagsIndex);
        int CountriesIndex = info.indexOf("制片国家/地区:", officialWebsiteIndex);
        getTags(info, tagsIndex, officialWebsiteIndex, CountriesIndex, movieView);
        int languageIndex = info.indexOf("语言:", CountriesIndex);
        getCountries(info, CountriesIndex, languageIndex, movieView);
        int releaseDateIndex = info.indexOf("上映日期:", languageIndex);
        int lengthIndex = info.indexOf("片长:", releaseDateIndex);
        getReleaseDate(info, releaseDateIndex, lengthIndex, movieView);
        getIntroduction(content, movieView);
        getName(content, movieView);
        getPosterUrl(content, movieView);
        getScore(content, movieView);
    }

    private void getDirectors(String info, int directorsIndex, int scriptwriterIndex, MovieView movieView) {
        var directors = info.substring(directorsIndex, scriptwriterIndex).trim().split(": ");
        var directorViewSet = Arrays.asList(directors[1].split(" / ")).stream().map((director) -> {
            return new DirectorView(director);
        }).collect(Collectors.toSet());
        movieView.setDirectorViewSet(directorViewSet);
    }

    private void getStarrings(String info, int starringsIndex, int tagssIndex, MovieView movieView) {
        var starrings = info.substring(starringsIndex, tagssIndex).trim().split(": ");
        var starringViewSet = Arrays.asList(starrings[1].split(" / ")).stream().map((starring) -> {
            return new StarringView(starring);
        }).collect(Collectors.toSet());
        movieView.setStarringViewSet(starringViewSet);
    }

    private void getTags(String info, int tagsIndex, int officialWebsiteIndex, int countriesIndex,
            MovieView movieView) {
        String[] tags = {};
        if (officialWebsiteIndex == -1) {
            tags = info.substring(tagsIndex, countriesIndex).trim().split(": ");
        } else {
            tags = info.substring(tagsIndex, officialWebsiteIndex).trim().split(": ");
        }
        var tagsViewSet = Arrays.asList(tags[1].split(" / ")).stream().map((tag) -> {
            return new TagView(tag);
        }).collect(Collectors.toSet());
        movieView.setTagViewSet(tagsViewSet);
    }

    private void getCountries(String info, int countriesIndex, int languageIndex, MovieView movieView) {
        var countries = info.substring(countriesIndex, languageIndex).trim().split(": ");
        var countryViewSet = Arrays.asList(countries[1].split(" / ")).stream().map((country) -> {
            return new CountryView(country);
        }).collect(Collectors.toSet());
        movieView.setCountryViewSet(countryViewSet);
    }

    private void getReleaseDate(String info, int releaseDateIndex, int lengthIndex, MovieView movieView) {
        var releaseDates = info.substring(releaseDateIndex, lengthIndex).trim().split(": ");
        var date = Arrays.asList(releaseDates[1].split(" / ")).stream().map((releaseDate) -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
            } catch (ParseException e) {
                return new Date();
            }
        }).min((date1, date2) -> {
            return (int) (date1.getTime() - date2.getTime());
        }).orElseThrow();
        movieView.setReleaseDate(date);
    }

    private void getIntroduction(Element content, MovieView movieView) {
        movieView.setIntroduction(
                content.getElementsByClass("related-info").get(0).getElementsByClass("indent").get(0).text());
    }

    private void getName(Element content, MovieView movieView) {
        var name = content.getElementsByTag("h1").get(0).getElementsByTag("span").get(0).text();
        var chineseNameEndIndex = name.indexOf(" ");
        if (chineseNameEndIndex == -1) {
            movieView.setChineseName(name);
            movieView.setOriginName(name);
        } else {
            movieView.setChineseName(name.substring(0, chineseNameEndIndex));
            movieView.setOriginName(name.substring(chineseNameEndIndex));
        }
    }

    private void getPosterUrl(Element content, MovieView movieView) {
        movieView.setPosterUrl(content.getElementById("mainpic").getElementsByTag("img").get(0).attr("src"));
    }

    private void getScore(Element content, MovieView movieView) {
        var score = content.getElementById("interest_sectl").getElementsByTag("strong").get(0).text();
        movieView.setScore(Double.parseDouble(score));
    }

    private Element getContent(String url) {
        var html = new RestTemplate().getForObject(url, String.class);
        var document = Jsoup.parse(html);
        return document.getElementById("content");
    }
}