import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useCardStore = defineStore('card', () => {
  const REST_PJT_URL_CARDS = `http://localhost:8080/cards`
  const userCollectedCardData = ref([])
  const userRecentlyCollectedCardData = ref([])

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

  return {
    userCollectedCardData,
    userRecentlyCollectedCardData,
    getUserCollectedCardData,
    getUserRecentlyCollectedCardData,
    getRandomlySelectedExerciseData,
  }
})
