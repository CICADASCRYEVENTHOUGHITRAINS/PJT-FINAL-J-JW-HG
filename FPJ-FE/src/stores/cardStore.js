import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useCardStore = defineStore('card', () => {
  const REST_PJT_URL_CARDS = `http://localhost:8080/cards`
  const REST_PJT_URL_EXERCISE = `http://localhost:8080/excercise`

  const userCollectedCardData = ref([{}, {}, {}])
  const userRecentlyCollectedCardData = ref([])
  const generatedCardStackData = ref([])

  /** 유저별 카드 정보 중 개별 카드 정보를 가져옵니다.
   * @function getCardFromCollectedCardData
   * @param {number} cardId - 카드 고유 id
   * @returns {Promise<Card[]>}
   * @throws {Error}
   */
  const getCardFromCollectedCardData = async (cardId) => {
    try {
      const res = await axios({
        url: `${REST_PJT_URL_CARDS}/${cardId}`,
        method: 'GET',
      })
      const cards = res.data.value
      userCollectedCardData.value = cards
    } catch (err) {
      console.error(`Error fetching (${err})`)
    }
  }

  // userStore 구현 필요 --------------------------------------------------------------------------------
  /** 유저별 카드 정보를 가져옵니다.
   * @function getUserCollectedCardData
   * @param {number} userId - 유저 고유 id
   * @returns {Promise<Card[]>}
   * @throws {Error}
   */
  const getUserCollectedCardData = async (userId) => {
    try {
      const res = await axios({
        url: `${REST_PJT_URL_CARDS}/${userId}`,
        method: 'GET',
      })
      const cards = res.data
      userCollectedCardData.value = cards
    } catch (err) {
      console.error(`Error fetching (${err})`)
    }
  }

  /** 유저의 최근 카드정보를 조회합니다.
   * @function getUserRecentCards
   * @params {number} userId
   * @returns {Promise<Card[]>}
   * @throws {Error}
   */

  const getUserRecentlyCollectedCardData = async (userId) => {
    try {
      const res = await axios({
        url: `${REST_PJT_URL_CARDS}/${userId}/recent`,
        method: 'GET',
      })
      const cards = res.data
      userRecentlyCollectedCardData.value = cards
    } catch (err) {
      console.error(`Error fetching recent card data. (${err})`)
    }
  }

  // // exerciseStore로 분리 ----------------------------------------------------------------------------
  // /** 카드에 등록할 운동 정보를 받아옵니다.
  //  * @function getRandomlySelectedExerciseData
  //  * @async
  //  * @returns {Promise<Exercise[]>}
  //  * @throws {Error}
  //  */
  // const getRandomlySelectedExerciseData = async () => {
  //   try {
  //     const res = await axios({
  //       url: `${REST_PJT_URL_EXERCISE}`,
  //       method: 'GET',
  //     })
  //     const excercises = res.data
  //     console.log(excercises)
  //     generatedCardStackData.value = excercises
  //   } catch (err) {
  //     console.error(`Error fetching excercise data. (${err})`)
  //   }
  // }

  return {
    userCollectedCardData,
    userRecentlyCollectedCardData,
    getUserCollectedCardData,
    getUserRecentlyCollectedCardData,
  }
})
