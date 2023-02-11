use std::fmt::Display;
use std::fs::File;
use std::io::{BufRead, BufReader};
use std::iter::FilterMap;

pub mod day01;

pub trait Day<T, V>
    where T: Display + Sized,
          V: Display + Sized {
    fn part1(&self) -> T;

    fn part2(&self) -> V;

    fn run(&self) {
        let part1 = self.part1();
        let part2 = self.part2();
        println!("-Part 1: {}\n-Part 2: {}", part1, part2);
    }
}

type FilteredLines = FilterMap<std::io::Lines<BufReader<File>>, fn(Result<String, std::io::Error>) -> Option<String>>;

pub fn read_lines(filename: &str) -> FilteredLines {
    let file = File::open(filename).expect("Not found");
    BufReader::new(file).lines().filter_map(Result::ok)
}

pub fn default_input(day: u8) -> String {
    format!("../input/{:0>2}/input.txt", day)
}