import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

const useCardStore = defineStore('card', () => {
  const REST_PJT_URL_CARDS = `http://localhost:8080/cards/`
  const REST_PJT_URL_EXERCISE = `http://localhost:8080/excercise/`

  const userCollectedCardsData = ref([])
  const generatedCardStackData = ref([])

  const DUMMY_CARDSTACK_DATA = ref([
    // DB에서 들어올 때 3개만 들어옴
    {
      id: 1,
      part: '손목',
      name: '손목 스트레칭',
      info: '손바닥을 위로 향하게 하고 반대손으로 손가락을 아래로 당기기',
      time: 30,
    },
    {
      id: 2,
      part: '목',
      name: '목 스트레칭',
      info: '머리를 좌우로 천천히 기울이기',
      time: 20,
    },
    {
      id: 3,
      part: '어깨',
      name: '어깨 스트레칭',
      info: '어깨를 위아래로 올렸다 내리기',
      time: 40,
    },
  ])

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
      const cards = res.data
      console.log(cards)
      userCollectedCardsData.value = cards
    } catch (err) {
      console.error(`Error fetching (${err})`)
    }
  }

  // userStore 구현 필요 --------------------------------------------------------------------------------
  // /** 유저별 카드 정보를 가져옵니다.
  //  * @function getUserCollectedCardData
  //  * @param {number} id - 유저 고유 id
  //  * @returns {Promise<Card[]>}
  //  * @throws {Error}
  //  */
  // const getUserCollectedCardData = async (userId) => {
  //   try {
  //     const res = await axios({
  //       url: `${REST_PJT_URL_CARDS}/${userId}`,
  //       method: 'GET',
  //     })
  //     const cards = res.data
  //     console.log(cards)
  //     userCollectedCardsData.value = cards
  //   } catch (err) {
  //     console.error(`Error fetching (${err})`)
  //   }
  // }
  //
  // /** 유저의 최근 카드정보를 조회합니다.
  //  * @function getUserRecentCards
  //  * @params {number} userId
  //  * @returns {Promise<Card[]>}
  //  * @throws {Error}
  //  */
  //
  // const getUserRecentlyCollectedCards = async (userId) => {
  //   try {
  //     const res = await axios({
  //       url: `${REST_PJT_URL_CARDS}/${userId}`,
  //       method: 'GET',
  //     })
  //     const cards = res.data
  //     console.log(cards)
  //     recentCards.value = cards
  //   } catch (err) {
  //     console.error(`Error fetching recent card data. (${err})`)
  //   }
  // }
  //

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

  return { data, getRandomlySelectedExerciseData, DUMMY_CARDSTACK_DATA }
})
