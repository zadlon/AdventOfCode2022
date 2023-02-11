use crate::{Day, read_lines};

pub struct Day1 {
    input: Vec<u32>,
}

impl Day1 {
    pub fn from(filename: &str) -> Day1 {
        let input = read_lines(filename)
            .collect::<Vec<String>>()
            .split(|s| s.is_empty())
            .map(|lines| {
                let mut sum: u32 = 0;
                for line in lines {
                    sum += line.parse::<u32>().unwrap()
                }
                sum
            })
            .collect();
        Day1 { input }
    }
}

impl Day<u32, u32> for Day1 {
    fn part1(&self) -> u32 {
        self.input.iter().max().copied().unwrap()
    }

    fn part2(&self) -> u32 {
        let mut a = 0;
        let mut b = 0;
        let mut c = 0;

        for calories in self.input.iter() {
            let calories = *calories;
            if calories > a {
                c = b;
                b = a;
                a = calories;
            } else if calories > b {
                c = b;
                b = calories;
            } else if calories > c {
                c = calories;
            }
        }
        a + b + c
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn part1() {
        assert_eq!(input().part1(), 24000)
    }

    #[test]
    fn part2() {
        assert_eq!(input().part2(), 45000)
    }

    fn input() -> Day1 {
        Day1::from("../input/01/test.txt")
    }
}