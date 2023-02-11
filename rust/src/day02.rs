use crate::{Day, read_lines};

pub struct Day2 {
    input: Vec<(Shape, char)>,
}

#[derive(PartialEq)]
enum Shape {
    Rock,
    Paper,
    Scissors,
}

impl Shape {
    fn from_opponent(c: char) -> Shape {
        match c {
            'A' => Shape::Rock,
            'B' => Shape::Paper,
            'C' => Shape::Scissors,
            _ => panic!("unexpected character {} for shape", c)
        }
    }

    fn score(&self) -> u32 {
        match self {
            Shape::Rock => 1,
            Shape::Paper => 2,
            Shape::Scissors => 3
        }
    }

    fn wins_over(&self) -> Shape {
        match self {
            Shape::Rock => Shape::Scissors,
            Shape::Paper => Shape::Rock,
            Shape::Scissors => Shape::Paper
        }
    }

    fn loses_over(&self) -> Shape {
        match self {
            Shape::Rock => Shape::Paper,
            Shape::Paper => Shape::Scissors,
            Shape::Scissors => Shape::Rock
        }
    }

    fn outcome(&self, other: &Shape) -> u32 {
        if self == other {
            3
        } else if &self.wins_over() == other {
            6
        } else {
            0
        }
    }
}

impl Day2 {
    pub fn from(filename: &str) -> Day2 {
        let input = read_lines(filename)
            .map(|s| {
                let chars: Vec<&str> = s.split(" ").collect();
                (Shape::from_opponent(chars[0].chars().nth(0).unwrap()), chars[1].chars().nth(0).unwrap())
            })
            .collect();
        Day2 { input }
    }
}

impl Day<u32, u32> for Day2 {
    fn part1(&self) -> u32 {
        self.input.iter()
            .map(|(opponent_shape, my)| {
                let my_shape = match my {
                    'X' => Shape::Rock,
                    'Y' => Shape::Paper,
                    'Z' => Shape::Scissors,
                    _ => panic!("Unrecognized char: {}", my)
                };
                my_shape.score() + my_shape.outcome(opponent_shape)
            })
            .sum()
    }

    fn part2(&self) -> u32 {
        self.input.iter()
            .map(|(opponent_shape, my)| {
                match my {
                    'X' => 0 + opponent_shape.wins_over().score(),
                    'Y' => 3 + opponent_shape.score(),
                    'Z' => 6 + opponent_shape.loses_over().score(),
                    _ => panic!("Unrecognized char: {}", my)
                }
            })
            .sum()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn part1() {
        assert_eq!(input().part1(), 15)
    }

    #[test]
    fn part2() {
        assert_eq!(input().part2(), 12)
    }

    fn input() -> Day2 {
        Day2::from("../input/02/test.txt")
    }
}