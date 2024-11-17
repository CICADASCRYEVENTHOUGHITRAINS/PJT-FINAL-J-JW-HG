import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useExerciseStore = defineStore('exercise', () => {
  const REST_PJT_URL_EXERCISE = `http://localhost:8080/exercise`
  const randomlySelectedExerciseData = ref([{}, {}, {}])

  /** 카드에 등록할 운동 정보를 받아옵니다.
   * @function getRandomlySelectedExerciseData
   * @async
   * @returns {Promise<Exercise[]>}
   * @throws {Error}
   */
  const getRandomlySelectedExerciseData = async () => {
    try {
      const res = await axios({
        url: `${REST_PJT_URL_EXERCISE}/random`,
        method: 'GET',
      })
      const excercises = res.data
      console.log(excercises)
      randomlySelectedExerciseData.value = excercises
    } catch (err) {
      console.error(`Error fetching excercise data. (${err})`)
    }
  }

  return { randomlySelectedExerciseData, getRandomlySelectedExerciseData }
})
